import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProjetFormService, ProjetFormGroup } from './projet-form.service';
import { IProjet } from '../projet.model';
import { ProjetService } from '../service/projet.service';
import { IUserSopra } from 'app/entities/user-sopra/user-sopra.model';
import { UserSopraService } from 'app/entities/user-sopra/service/user-sopra.service';

@Component({
  selector: 'jhi-projet-update',
  templateUrl: './projet-update.component.html',
})
export class ProjetUpdateComponent implements OnInit {
  isSaving = false;
  projet: IProjet | null = null;

  userSoprasSharedCollection: IUserSopra[] = [];

  editForm: ProjetFormGroup = this.projetFormService.createProjetFormGroup();

  constructor(
    protected projetService: ProjetService,
    protected projetFormService: ProjetFormService,
    protected userSopraService: UserSopraService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUserSopra = (o1: IUserSopra | null, o2: IUserSopra | null): boolean => this.userSopraService.compareUserSopra(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projet }) => {
      this.projet = projet;
      if (projet) {
        this.updateForm(projet);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projet = this.projetFormService.getProjet(this.editForm);
    if (projet.id !== null) {
      this.subscribeToSaveResponse(this.projetService.update(projet));
    } else {
      this.subscribeToSaveResponse(this.projetService.create(projet));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjet>>): void {
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

  protected updateForm(projet: IProjet): void {
    this.projet = projet;
    this.projetFormService.resetForm(this.editForm, projet);

    this.userSoprasSharedCollection = this.userSopraService.addUserSopraToCollectionIfMissing<IUserSopra>(
      this.userSoprasSharedCollection,
      ...(projet.projectMenbers ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userSopraService
      .query()
      .pipe(map((res: HttpResponse<IUserSopra[]>) => res.body ?? []))
      .pipe(
        map((userSopras: IUserSopra[]) =>
          this.userSopraService.addUserSopraToCollectionIfMissing<IUserSopra>(userSopras, ...(this.projet?.projectMenbers ?? []))
        )
      )
      .subscribe((userSopras: IUserSopra[]) => (this.userSoprasSharedCollection = userSopras));
  }
}
