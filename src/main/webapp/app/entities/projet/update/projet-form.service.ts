import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProjet, NewProjet } from '../projet.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProjet for edit and NewProjetFormGroupInput for create.
 */
type ProjetFormGroupInput = IProjet | PartialWithRequiredKeyOf<NewProjet>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProjet | NewProjet> = Omit<T, 'dateDebut' | 'dateFin'> & {
  dateDebut?: string | null;
  dateFin?: string | null;
};

type ProjetFormRawValue = FormValueOf<IProjet>;

type NewProjetFormRawValue = FormValueOf<NewProjet>;

type ProjetFormDefaults = Pick<NewProjet, 'id' | 'dateDebut' | 'dateFin' | 'projectMenbers'>;

type ProjetFormGroupContent = {
  id: FormControl<ProjetFormRawValue['id'] | NewProjet['id']>;
  nomProjet: FormControl<ProjetFormRawValue['nomProjet']>;
  paysProjet: FormControl<ProjetFormRawValue['paysProjet']>;
  regionProjet: FormControl<ProjetFormRawValue['regionProjet']>;
  dateDebut: FormControl<ProjetFormRawValue['dateDebut']>;
  nombreRessource: FormControl<ProjetFormRawValue['nombreRessource']>;
  dateFin: FormControl<ProjetFormRawValue['dateFin']>;
  competanceDemander: FormControl<ProjetFormRawValue['competanceDemander']>;
  projectMenbers: FormControl<ProjetFormRawValue['projectMenbers']>;
};

export type ProjetFormGroup = FormGroup<ProjetFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjetFormService {
  createProjetFormGroup(projet: ProjetFormGroupInput = { id: null }): ProjetFormGroup {
    const projetRawValue = this.convertProjetToProjetRawValue({
      ...this.getFormDefaults(),
      ...projet,
    });
    return new FormGroup<ProjetFormGroupContent>({
      id: new FormControl(
        { value: projetRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nomProjet: new FormControl(projetRawValue.nomProjet),
      paysProjet: new FormControl(projetRawValue.paysProjet),
      regionProjet: new FormControl(projetRawValue.regionProjet),
      dateDebut: new FormControl(projetRawValue.dateDebut),
      nombreRessource: new FormControl(projetRawValue.nombreRessource),
      dateFin: new FormControl(projetRawValue.dateFin),
      competanceDemander: new FormControl(projetRawValue.competanceDemander),
      projectMenbers: new FormControl(projetRawValue.projectMenbers ?? []),
    });
  }

  getProjet(form: ProjetFormGroup): IProjet | NewProjet {
    return this.convertProjetRawValueToProjet(form.getRawValue() as ProjetFormRawValue | NewProjetFormRawValue);
  }

  resetForm(form: ProjetFormGroup, projet: ProjetFormGroupInput): void {
    const projetRawValue = this.convertProjetToProjetRawValue({ ...this.getFormDefaults(), ...projet });
    form.reset(
      {
        ...projetRawValue,
        id: { value: projetRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProjetFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateDebut: currentTime,
      dateFin: currentTime,
      projectMenbers: [],
    };
  }

  private convertProjetRawValueToProjet(rawProjet: ProjetFormRawValue | NewProjetFormRawValue): IProjet | NewProjet {
    return {
      ...rawProjet,
      dateDebut: dayjs(rawProjet.dateDebut, DATE_TIME_FORMAT),
      dateFin: dayjs(rawProjet.dateFin, DATE_TIME_FORMAT),
    };
  }

  private convertProjetToProjetRawValue(
    projet: IProjet | (Partial<NewProjet> & ProjetFormDefaults)
  ): ProjetFormRawValue | PartialWithRequiredKeyOf<NewProjetFormRawValue> {
    return {
      ...projet,
      dateDebut: projet.dateDebut ? projet.dateDebut.format(DATE_TIME_FORMAT) : undefined,
      dateFin: projet.dateFin ? projet.dateFin.format(DATE_TIME_FORMAT) : undefined,
      projectMenbers: projet.projectMenbers ?? [],
    };
  }
}
