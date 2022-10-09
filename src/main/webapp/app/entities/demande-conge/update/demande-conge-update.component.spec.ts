import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DemandeCongeFormService } from './demande-conge-form.service';
import { DemandeCongeService } from '../service/demande-conge.service';
import { IDemandeConge } from '../demande-conge.model';

import { DemandeCongeUpdateComponent } from './demande-conge-update.component';

describe('DemandeConge Management Update Component', () => {
  let comp: DemandeCongeUpdateComponent;
  let fixture: ComponentFixture<DemandeCongeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let demandeCongeFormService: DemandeCongeFormService;
  let demandeCongeService: DemandeCongeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DemandeCongeUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DemandeCongeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DemandeCongeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    demandeCongeFormService = TestBed.inject(DemandeCongeFormService);
    demandeCongeService = TestBed.inject(DemandeCongeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const demandeConge: IDemandeConge = { id: 456 };

      activatedRoute.data = of({ demandeConge });
      comp.ngOnInit();

      expect(comp.demandeConge).toEqual(demandeConge);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeConge>>();
      const demandeConge = { id: 123 };
      jest.spyOn(demandeCongeFormService, 'getDemandeConge').mockReturnValue(demandeConge);
      jest.spyOn(demandeCongeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeConge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeConge }));
      saveSubject.complete();

      // THEN
      expect(demandeCongeFormService.getDemandeConge).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(demandeCongeService.update).toHaveBeenCalledWith(expect.objectContaining(demandeConge));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeConge>>();
      const demandeConge = { id: 123 };
      jest.spyOn(demandeCongeFormService, 'getDemandeConge').mockReturnValue({ id: null });
      jest.spyOn(demandeCongeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeConge: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeConge }));
      saveSubject.complete();

      // THEN
      expect(demandeCongeFormService.getDemandeConge).toHaveBeenCalled();
      expect(demandeCongeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeConge>>();
      const demandeConge = { id: 123 };
      jest.spyOn(demandeCongeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeConge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(demandeCongeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
