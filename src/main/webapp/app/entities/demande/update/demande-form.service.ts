import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDemande, NewDemande } from '../demande.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDemande for edit and NewDemandeFormGroupInput for create.
 */
type DemandeFormGroupInput = IDemande | PartialWithRequiredKeyOf<NewDemande>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDemande | NewDemande> = Omit<T, 'startDemande' | 'endDemande' | 'statusDemande'> & {
  startDemande?: string | null;
  endDemande?: string | null;
  statusDemande?: string | null;
};

type DemandeFormRawValue = FormValueOf<IDemande>;

type NewDemandeFormRawValue = FormValueOf<NewDemande>;

type DemandeFormDefaults = Pick<NewDemande, 'id' | 'startDemande' | 'endDemande' | 'statusDemande'>;

type DemandeFormGroupContent = {
  id: FormControl<DemandeFormRawValue['id'] | NewDemande['id']>;
  startDemande: FormControl<DemandeFormRawValue['startDemande']>;
  endDemande: FormControl<DemandeFormRawValue['endDemande']>;
  statusDemande: FormControl<DemandeFormRawValue['statusDemande']>;
  userSopra: FormControl<DemandeFormRawValue['userSopra']>;
};

export type DemandeFormGroup = FormGroup<DemandeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DemandeFormService {
  createDemandeFormGroup(demande: DemandeFormGroupInput = { id: null }): DemandeFormGroup {
    const demandeRawValue = this.convertDemandeToDemandeRawValue({
      ...this.getFormDefaults(),
      ...demande,
    });
    return new FormGroup<DemandeFormGroupContent>({
      id: new FormControl(
        { value: demandeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      startDemande: new FormControl(demandeRawValue.startDemande),
      endDemande: new FormControl(demandeRawValue.endDemande),
      statusDemande: new FormControl(demandeRawValue.statusDemande),
      userSopra: new FormControl(demandeRawValue.userSopra),
    });
  }

  getDemande(form: DemandeFormGroup): IDemande | NewDemande {
    return this.convertDemandeRawValueToDemande(form.getRawValue() as DemandeFormRawValue | NewDemandeFormRawValue);
  }

  resetForm(form: DemandeFormGroup, demande: DemandeFormGroupInput): void {
    const demandeRawValue = this.convertDemandeToDemandeRawValue({ ...this.getFormDefaults(), ...demande });
    form.reset(
      {
        ...demandeRawValue,
        id: { value: demandeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DemandeFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      startDemande: currentTime,
      endDemande: currentTime,
      statusDemande: currentTime,
    };
  }

  private convertDemandeRawValueToDemande(rawDemande: DemandeFormRawValue | NewDemandeFormRawValue): IDemande | NewDemande {
    return {
      ...rawDemande,
      startDemande: dayjs(rawDemande.startDemande, DATE_TIME_FORMAT),
      endDemande: dayjs(rawDemande.endDemande, DATE_TIME_FORMAT),
      statusDemande: dayjs(rawDemande.statusDemande, DATE_TIME_FORMAT),
    };
  }

  private convertDemandeToDemandeRawValue(
    demande: IDemande | (Partial<NewDemande> & DemandeFormDefaults)
  ): DemandeFormRawValue | PartialWithRequiredKeyOf<NewDemandeFormRawValue> {
    return {
      ...demande,
      startDemande: demande.startDemande ? demande.startDemande.format(DATE_TIME_FORMAT) : undefined,
      endDemande: demande.endDemande ? demande.endDemande.format(DATE_TIME_FORMAT) : undefined,
      statusDemande: demande.statusDemande ? demande.statusDemande.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
