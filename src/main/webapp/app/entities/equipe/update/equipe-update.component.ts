import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EquipeFormService, EquipeFormGroup } from './equipe-form.service';
import { IEquipe } from '../equipe.model';
import { EquipeService } from '../service/equipe.service';
import { IUserSopra } from 'app/entities/user-sopra/user-sopra.model';
import { UserSopraService } from 'app/entities/user-sopra/service/user-sopra.service';

@Component({
  selector: 'jhi-equipe-update',
  templateUrl: './equipe-update.component.html',
})
export class EquipeUpdateComponent implements OnInit {
  isSaving = false;
  equipe: IEquipe | null = null;

  userSoprasSharedCollection: IUserSopra[] = [];

  editForm: EquipeFormGroup = this.equipeFormService.createEquipeFormGroup();

  constructor(
    protected equipeService: EquipeService,
    protected equipeFormService: EquipeFormService,
    protected userSopraService: UserSopraService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUserSopra = (o1: IUserSopra | null, o2: IUserSopra | null): boolean => this.userSopraService.compareUserSopra(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equipe }) => {
      this.equipe = equipe;
      if (equipe) {
        this.updateForm(equipe);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const equipe = this.equipeFormService.getEquipe(this.editForm);
    if (equipe.id !== null) {
      this.subscribeToSaveResponse(this.equipeService.update(equipe));
    } else {
      this.subscribeToSaveResponse(this.equipeService.create(equipe));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipe>>): void {
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

  protected updateForm(equipe: IEquipe): void {
    this.equipe = equipe;
    this.equipeFormService.resetForm(this.editForm, equipe);

    this.userSoprasSharedCollection = this.userSopraService.addUserSopraToCollectionIfMissing<IUserSopra>(
      this.userSoprasSharedCollection,
      ...(equipe.equipeusers ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userSopraService
      .query()
      .pipe(map((res: HttpResponse<IUserSopra[]>) => res.body ?? []))
      .pipe(
        map((userSopras: IUserSopra[]) =>
          this.userSopraService.addUserSopraToCollectionIfMissing<IUserSopra>(userSopras, ...(this.equipe?.equipeusers ?? []))
        )
      )
      .subscribe((userSopras: IUserSopra[]) => (this.userSoprasSharedCollection = userSopras));
  }
}
