import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DemandeCongeFormService, DemandeCongeFormGroup } from './demande-conge-form.service';
import { IDemandeConge } from '../demande-conge.model';
import { DemandeCongeService } from '../service/demande-conge.service';
import { StatusDemande } from 'app/entities/enumerations/status-demande.model';

@Component({
  selector: 'jhi-demande-conge-update',
  templateUrl: './demande-conge-update.component.html',
})
export class DemandeCongeUpdateComponent implements OnInit {
  isSaving = false;
  demandeConge: IDemandeConge | null = null;
  statusDemandeValues = Object.keys(StatusDemande);

  editForm: DemandeCongeFormGroup = this.demandeCongeFormService.createDemandeCongeFormGroup();

  constructor(
    protected demandeCongeService: DemandeCongeService,
    protected demandeCongeFormService: DemandeCongeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeConge }) => {
      this.demandeConge = demandeConge;
      if (demandeConge) {
        this.updateForm(demandeConge);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandeConge = this.demandeCongeFormService.getDemandeConge(this.editForm);
    if (demandeConge.id !== null) {
      this.subscribeToSaveResponse(this.demandeCongeService.update(demandeConge));
    } else {
      this.subscribeToSaveResponse(this.demandeCongeService.create(demandeConge));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeConge>>): void {
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

  protected updateForm(demandeConge: IDemandeConge): void {
    this.demandeConge = demandeConge;
    this.demandeCongeFormService.resetForm(this.editForm, demandeConge);
  }
}
