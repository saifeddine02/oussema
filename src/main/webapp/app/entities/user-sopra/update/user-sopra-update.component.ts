import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { UserSopraFormService, UserSopraFormGroup } from './user-sopra-form.service';
import { IUserSopra } from '../user-sopra.model';
import { UserSopraService } from '../service/user-sopra.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IResponsable } from 'app/entities/responsable/responsable.model';
import { ResponsableService } from 'app/entities/responsable/service/responsable.service';
import { UserRolesSopra } from 'app/entities/enumerations/user-roles-sopra.model';

@Component({
  selector: 'jhi-user-sopra-update',
  templateUrl: './user-sopra-update.component.html',
})
export class UserSopraUpdateComponent implements OnInit {
  isSaving = false;
  userSopra: IUserSopra | null = null;
  userRolesSopraValues = Object.keys(UserRolesSopra);

  responsablesCollection: IResponsable[] = [];

  editForm: UserSopraFormGroup = this.userSopraFormService.createUserSopraFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected userSopraService: UserSopraService,
    protected userSopraFormService: UserSopraFormService,
    protected responsableService: ResponsableService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareResponsable = (o1: IResponsable | null, o2: IResponsable | null): boolean => this.responsableService.compareResponsable(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userSopra }) => {
      this.userSopra = userSopra;
      if (userSopra) {
        this.updateForm(userSopra);
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
    const userSopra = this.userSopraFormService.getUserSopra(this.editForm);
    if (userSopra.id !== null) {
      this.subscribeToSaveResponse(this.userSopraService.update(userSopra));
    } else {
      this.subscribeToSaveResponse(this.userSopraService.create(userSopra));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserSopra>>): void {
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

  protected updateForm(userSopra: IUserSopra): void {
    this.userSopra = userSopra;
    this.userSopraFormService.resetForm(this.editForm, userSopra);

    this.responsablesCollection = this.responsableService.addResponsableToCollectionIfMissing<IResponsable>(
      this.responsablesCollection,
      userSopra.responsable
    );
  }

  protected loadRelationshipsOptions(): void {
    this.responsableService
      .query({ filter: 'responsable-is-null' })
      .pipe(map((res: HttpResponse<IResponsable[]>) => res.body ?? []))
      .pipe(
        map((responsables: IResponsable[]) =>
          this.responsableService.addResponsableToCollectionIfMissing<IResponsable>(responsables, this.userSopra?.responsable)
        )
      )
      .subscribe((responsables: IResponsable[]) => (this.responsablesCollection = responsables));
  }
}
