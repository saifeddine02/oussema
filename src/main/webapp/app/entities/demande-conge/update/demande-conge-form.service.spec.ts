import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../demande-conge.test-samples';

import { DemandeCongeFormService } from './demande-conge-form.service';

describe('DemandeConge Form Service', () => {
  let service: DemandeCongeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DemandeCongeFormService);
  });

  describe('Service methods', () => {
    describe('createDemandeCongeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDemandeCongeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creationDate: expect.any(Object),
            avisTl: expect.any(Object),
            statusDemande: expect.any(Object),
            dateDebutConge: expect.any(Object),
            dateFinConge: expect.any(Object),
            nombreJour: expect.any(Object),
          })
        );
      });

      it('passing IDemandeConge should create a new form with FormGroup', () => {
        const formGroup = service.createDemandeCongeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creationDate: expect.any(Object),
            avisTl: expect.any(Object),
            statusDemande: expect.any(Object),
            dateDebutConge: expect.any(Object),
            dateFinConge: expect.any(Object),
            nombreJour: expect.any(Object),
          })
        );
      });
    });

    describe('getDemandeConge', () => {
      it('should return NewDemandeConge for default DemandeConge initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDemandeCongeFormGroup(sampleWithNewData);

        const demandeConge = service.getDemandeConge(formGroup) as any;

        expect(demandeConge).toMatchObject(sampleWithNewData);
      });

      it('should return NewDemandeConge for empty DemandeConge initial value', () => {
        const formGroup = service.createDemandeCongeFormGroup();

        const demandeConge = service.getDemandeConge(formGroup) as any;

        expect(demandeConge).toMatchObject({});
      });

      it('should return IDemandeConge', () => {
        const formGroup = service.createDemandeCongeFormGroup(sampleWithRequiredData);

        const demandeConge = service.getDemandeConge(formGroup) as any;

        expect(demandeConge).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDemandeConge should not enable id FormControl', () => {
        const formGroup = service.createDemandeCongeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDemandeConge should disable id FormControl', () => {
        const formGroup = service.createDemandeCongeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
