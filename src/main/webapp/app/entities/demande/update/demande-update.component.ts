import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DemandeFormService, DemandeFormGroup } from './demande-form.service';
import { IDemande } from '../demande.model';
import { DemandeService } from '../service/demande.service';
import { IUserSopra } from 'app/entities/user-sopra/user-sopra.model';
import { UserSopraService } from 'app/entities/user-sopra/service/user-sopra.service';

@Component({
  selector: 'jhi-demande-update',
  templateUrl: './demande-update.component.html',
})
export class DemandeUpdateComponent implements OnInit {
  isSaving = false;
  demande: IDemande | null = null;

  userSoprasSharedCollection: IUserSopra[] = [];

  editForm: DemandeFormGroup = this.demandeFormService.createDemandeFormGroup();

  constructor(
    protected demandeService: DemandeService,
    protected demandeFormService: DemandeFormService,
    protected userSopraService: UserSopraService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUserSopra = (o1: IUserSopra | null, o2: IUserSopra | null): boolean => this.userSopraService.compareUserSopra(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demande }) => {
      this.demande = demande;
      if (demande) {
        this.updateForm(demande);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demande = this.demandeFormService.getDemande(this.editForm);
    if (demande.id !== null) {
      this.subscribeToSaveResponse(this.demandeService.update(demande));
    } else {
      this.subscribeToSaveResponse(this.demandeService.create(demande));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemande>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(demande: IDemande): void {
    this.demande = demande;
    this.demandeFormService.resetForm(this.editForm, demande);

    this.userSoprasSharedCollection = this.userSopraService.addUserSopraToCollectionIfMissing<IUserSopra>(
      this.userSoprasSharedCollection,
      demande.userSopra
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userSopraService
      .query()
      .pipe(map((res: HttpResponse<IUserSopra[]>) => res.body ?? []))
      .pipe(
        map((userSopras: IUserSopra[]) =>
          this.userSopraService.addUserSopraToCollectionIfMissing<IUserSopra>(userSopras, this.demande?.userSopra)
        )
      )
      .subscribe((userSopras: IUserSopra[]) => (this.userSoprasSharedCollection = userSopras));
  }
}
