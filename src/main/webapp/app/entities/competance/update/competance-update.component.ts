import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CompetanceFormService, CompetanceFormGroup } from './competance-form.service';
import { ICompetance } from '../competance.model';
import { CompetanceService } from '../service/competance.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUserSopra } from 'app/entities/user-sopra/user-sopra.model';
import { UserSopraService } from 'app/entities/user-sopra/service/user-sopra.service';

@Component({
  selector: 'jhi-competance-update',
  templateUrl: './competance-update.component.html',
})
export class CompetanceUpdateComponent implements OnInit {
  isSaving = false;
  competance: ICompetance | null = null;

  userSoprasSharedCollection: IUserSopra[] = [];

  editForm: CompetanceFormGroup = this.competanceFormService.createCompetanceFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected competanceService: CompetanceService,
    protected competanceFormService: CompetanceFormService,
    protected userSopraService: UserSopraService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUserSopra = (o1: IUserSopra | null, o2: IUserSopra | null): boolean => this.userSopraService.compareUserSopra(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competance }) => {
      this.competance = competance;
      if (competance) {
        this.updateForm(competance);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('staffingApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const competance = this.competanceFormService.getCompetance(this.editForm);
    if (competance.id !== null) {
      this.subscribeToSaveResponse(this.competanceService.update(competance));
    } else {
      this.subscribeToSaveResponse(this.competanceService.create(competance));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetance>>): void {
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

  protected updateForm(competance: ICompetance): void {
    this.competance = competance;
    this.competanceFormService.resetForm(this.editForm, competance);

    this.userSoprasSharedCollection = this.userSopraService.addUserSopraToCollectionIfMissing<IUserSopra>(
      this.userSoprasSharedCollection,
      competance.userSopra
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userSopraService
      .query()
      .pipe(map((res: HttpResponse<IUserSopra[]>) => res.body ?? []))
      .pipe(
        map((userSopras: IUserSopra[]) =>
          this.userSopraService.addUserSopraToCollectionIfMissing<IUserSopra>(userSopras, this.competance?.userSopra)
        )
      )
      .subscribe((userSopras: IUserSopra[]) => (this.userSoprasSharedCollection = userSopras));
  }
}
