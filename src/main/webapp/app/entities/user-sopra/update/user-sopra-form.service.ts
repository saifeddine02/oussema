import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUserSopra, NewUserSopra } from '../user-sopra.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUserSopra for edit and NewUserSopraFormGroupInput for create.
 */
type UserSopraFormGroupInput = IUserSopra | PartialWithRequiredKeyOf<NewUserSopra>;

type UserSopraFormDefaults = Pick<NewUserSopra, 'id' | 'disponibiliteUser' | 'usersmembers' | 'usermembers'>;

type UserSopraFormGroupContent = {
  id: FormControl<IUserSopra['id'] | NewUserSopra['id']>;
  nomUser: FormControl<IUserSopra['nomUser']>;
  prenomUser: FormControl<IUserSopra['prenomUser']>;
  emailUser: FormControl<IUserSopra['emailUser']>;
  matriculeUser: FormControl<IUserSopra['matriculeUser']>;
  disponibiliteUser: FormControl<IUserSopra['disponibiliteUser']>;
  image: FormControl<IUserSopra['image']>;
  imageContentType: FormControl<IUserSopra['imageContentType']>;
  userRolesSopra: FormControl<IUserSopra['userRolesSopra']>;
  responsable: FormControl<IUserSopra['responsable']>;
  usersmembers: FormControl<IUserSopra['usersmembers']>;
  usermembers: FormControl<IUserSopra['usermembers']>;
};

export type UserSopraFormGroup = FormGroup<UserSopraFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UserSopraFormService {
  createUserSopraFormGroup(userSopra: UserSopraFormGroupInput = { id: null }): UserSopraFormGroup {
    const userSopraRawValue = {
      ...this.getFormDefaults(),
      ...userSopra,
    };
    return new FormGroup<UserSopraFormGroupContent>({
      id: new FormControl(
        { value: userSopraRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nomUser: new FormControl(userSopraRawValue.nomUser),
      prenomUser: new FormControl(userSopraRawValue.prenomUser),
      emailUser: new FormControl(userSopraRawValue.emailUser),
      matriculeUser: new FormControl(userSopraRawValue.matriculeUser),
      disponibiliteUser: new FormControl(userSopraRawValue.disponibiliteUser),
      image: new FormControl(userSopraRawValue.image),
      imageContentType: new FormControl(userSopraRawValue.imageContentType),
      userRolesSopra: new FormControl(userSopraRawValue.userRolesSopra),
      responsable: new FormControl(userSopraRawValue.responsable),
      usersmembers: new FormControl(userSopraRawValue.usersmembers ?? []),
      usermembers: new FormControl(userSopraRawValue.usermembers ?? []),
    });
  }

  getUserSopra(form: UserSopraFormGroup): IUserSopra | NewUserSopra {
    return form.getRawValue() as IUserSopra | NewUserSopra;
  }

  resetForm(form: UserSopraFormGroup, userSopra: UserSopraFormGroupInput): void {
    const userSopraRawValue = { ...this.getFormDefaults(), ...userSopra };
    form.reset(
      {
        ...userSopraRawValue,
        id: { value: userSopraRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): UserSopraFormDefaults {
    return {
      id: null,
      disponibiliteUser: false,
      usersmembers: [],
      usermembers: [],
    };
  }
}
