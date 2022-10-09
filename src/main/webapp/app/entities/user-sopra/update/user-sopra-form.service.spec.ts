import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../user-sopra.test-samples';

import { UserSopraFormService } from './user-sopra-form.service';

describe('UserSopra Form Service', () => {
  let service: UserSopraFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserSopraFormService);
  });

  describe('Service methods', () => {
    describe('createUserSopraFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUserSopraFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomUser: expect.any(Object),
            prenomUser: expect.any(Object),
            emailUser: expect.any(Object),
            matriculeUser: expect.any(Object),
            disponibiliteUser: expect.any(Object),
            image: expect.any(Object),
            userRolesSopra: expect.any(Object),
            responsable: expect.any(Object),
            usersmembers: expect.any(Object),
            usermembers: expect.any(Object),
          })
        );
      });

      it('passing IUserSopra should create a new form with FormGroup', () => {
        const formGroup = service.createUserSopraFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomUser: expect.any(Object),
            prenomUser: expect.any(Object),
            emailUser: expect.any(Object),
            matriculeUser: expect.any(Object),
            disponibiliteUser: expect.any(Object),
            image: expect.any(Object),
            userRolesSopra: expect.any(Object),
            responsable: expect.any(Object),
            usersmembers: expect.any(Object),
            usermembers: expect.any(Object),
          })
        );
      });
    });

    describe('getUserSopra', () => {
      it('should return NewUserSopra for default UserSopra initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createUserSopraFormGroup(sampleWithNewData);

        const userSopra = service.getUserSopra(formGroup) as any;

        expect(userSopra).toMatchObject(sampleWithNewData);
      });

      it('should return NewUserSopra for empty UserSopra initial value', () => {
        const formGroup = service.createUserSopraFormGroup();

        const userSopra = service.getUserSopra(formGroup) as any;

        expect(userSopra).toMatchObject({});
      });

      it('should return IUserSopra', () => {
        const formGroup = service.createUserSopraFormGroup(sampleWithRequiredData);

        const userSopra = service.getUserSopra(formGroup) as any;

        expect(userSopra).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUserSopra should not enable id FormControl', () => {
        const formGroup = service.createUserSopraFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUserSopra should disable id FormControl', () => {
        const formGroup = service.createUserSopraFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
