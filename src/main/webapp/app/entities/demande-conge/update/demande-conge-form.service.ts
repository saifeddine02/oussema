import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDemandeConge, NewDemandeConge } from '../demande-conge.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDemandeConge for edit and NewDemandeCongeFormGroupInput for create.
 */
type DemandeCongeFormGroupInput = IDemandeConge | PartialWithRequiredKeyOf<NewDemandeConge>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDemandeConge | NewDemandeConge> = Omit<T, 'creationDate' | 'dateDebutConge' | 'dateFinConge'> & {
  creationDate?: string | null;
  dateDebutConge?: string | null;
  dateFinConge?: string | null;
};

type DemandeCongeFormRawValue = FormValueOf<IDemandeConge>;

type NewDemandeCongeFormRawValue = FormValueOf<NewDemandeConge>;

type DemandeCongeFormDefaults = Pick<NewDemandeConge, 'id' | 'creationDate' | 'avisTl' | 'dateDebutConge' | 'dateFinConge'>;

type DemandeCongeFormGroupContent = {
  id: FormControl<DemandeCongeFormRawValue['id'] | NewDemandeConge['id']>;
  creationDate: FormControl<DemandeCongeFormRawValue['creationDate']>;
  avisTl: FormControl<DemandeCongeFormRawValue['avisTl']>;
  statusDemande: FormControl<DemandeCongeFormRawValue['statusDemande']>;
  dateDebutConge: FormControl<DemandeCongeFormRawValue['dateDebutConge']>;
  dateFinConge: FormControl<DemandeCongeFormRawValue['dateFinConge']>;
  nombreJour: FormControl<DemandeCongeFormRawValue['nombreJour']>;
};

export type DemandeCongeFormGroup = FormGroup<DemandeCongeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DemandeCongeFormService {
  createDemandeCongeFormGroup(demandeConge: DemandeCongeFormGroupInput = { id: null }): DemandeCongeFormGroup {
    const demandeCongeRawValue = this.convertDemandeCongeToDemandeCongeRawValue({
      ...this.getFormDefaults(),
      ...demandeConge,
    });
    return new FormGroup<DemandeCongeFormGroupContent>({
      id: new FormControl(
        { value: demandeCongeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      creationDate: new FormControl(demandeCongeRawValue.creationDate),
      avisTl: new FormControl(demandeCongeRawValue.avisTl),
      statusDemande: new FormControl(demandeCongeRawValue.statusDemande),
      dateDebutConge: new FormControl(demandeCongeRawValue.dateDebutConge),
      dateFinConge: new FormControl(demandeCongeRawValue.dateFinConge),
      nombreJour: new FormControl(demandeCongeRawValue.nombreJour),
    });
  }

  getDemandeConge(form: DemandeCongeFormGroup): IDemandeConge | NewDemandeConge {
    return this.convertDemandeCongeRawValueToDemandeConge(form.getRawValue() as DemandeCongeFormRawValue | NewDemandeCongeFormRawValue);
  }

  resetForm(form: DemandeCongeFormGroup, demandeConge: DemandeCongeFormGroupInput): void {
    const demandeCongeRawValue = this.convertDemandeCongeToDemandeCongeRawValue({ ...this.getFormDefaults(), ...demandeConge });
    form.reset(
      {
        ...demandeCongeRawValue,
        id: { value: demandeCongeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DemandeCongeFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      creationDate: currentTime,
      avisTl: false,
      dateDebutConge: currentTime,
      dateFinConge: currentTime,
    };
  }

  private convertDemandeCongeRawValueToDemandeConge(
    rawDemandeConge: DemandeCongeFormRawValue | NewDemandeCongeFormRawValue
  ): IDemandeConge | NewDemandeConge {
    return {
      ...rawDemandeConge,
      creationDate: dayjs(rawDemandeConge.creationDate, DATE_TIME_FORMAT),
      dateDebutConge: dayjs(rawDemandeConge.dateDebutConge, DATE_TIME_FORMAT),
      dateFinConge: dayjs(rawDemandeConge.dateFinConge, DATE_TIME_FORMAT),
    };
  }

  private convertDemandeCongeToDemandeCongeRawValue(
    demandeConge: IDemandeConge | (Partial<NewDemandeConge> & DemandeCongeFormDefaults)
  ): DemandeCongeFormRawValue | PartialWithRequiredKeyOf<NewDemandeCongeFormRawValue> {
    return {
      ...demandeConge,
      creationDate: demandeConge.creationDate ? demandeConge.creationDate.format(DATE_TIME_FORMAT) : undefined,
      dateDebutConge: demandeConge.dateDebutConge ? demandeConge.dateDebutConge.format(DATE_TIME_FORMAT) : undefined,
      dateFinConge: demandeConge.dateFinConge ? demandeConge.dateFinConge.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
