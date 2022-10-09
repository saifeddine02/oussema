import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserSopraFormService } from './user-sopra-form.service';
import { UserSopraService } from '../service/user-sopra.service';
import { IUserSopra } from '../user-sopra.model';
import { IResponsable } from 'app/entities/responsable/responsable.model';
import { ResponsableService } from 'app/entities/responsable/service/responsable.service';

import { UserSopraUpdateComponent } from './user-sopra-update.component';

describe('UserSopra Management Update Component', () => {
  let comp: UserSopraUpdateComponent;
  let fixture: ComponentFixture<UserSopraUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userSopraFormService: UserSopraFormService;
  let userSopraService: UserSopraService;
  let responsableService: ResponsableService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserSopraUpdateComponent],
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
      .overrideTemplate(UserSopraUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserSopraUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userSopraFormService = TestBed.inject(UserSopraFormService);
    userSopraService = TestBed.inject(UserSopraService);
    responsableService = TestBed.inject(ResponsableService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call responsable query and add missing value', () => {
      const userSopra: IUserSopra = { id: 456 };
      const responsable: IResponsable = { id: 16629 };
      userSopra.responsable = responsable;

      const responsableCollection: IResponsable[] = [{ id: 27855 }];
      jest.spyOn(responsableService, 'query').mockReturnValue(of(new HttpResponse({ body: responsableCollection })));
      const expectedCollection: IResponsable[] = [responsable, ...responsableCollection];
      jest.spyOn(responsableService, 'addResponsableToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ userSopra });
      comp.ngOnInit();

      expect(responsableService.query).toHaveBeenCalled();
      expect(responsableService.addResponsableToCollectionIfMissing).toHaveBeenCalledWith(responsableCollection, responsable);
      expect(comp.responsablesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const userSopra: IUserSopra = { id: 456 };
      const responsable: IResponsable = { id: 35768 };
      userSopra.responsable = responsable;

      activatedRoute.data = of({ userSopra });
      comp.ngOnInit();

      expect(comp.responsablesCollection).toContain(responsable);
      expect(comp.userSopra).toEqual(userSopra);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserSopra>>();
      const userSopra = { id: 123 };
      jest.spyOn(userSopraFormService, 'getUserSopra').mockReturnValue(userSopra);
      jest.spyOn(userSopraService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userSopra });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userSopra }));
      saveSubject.complete();

      // THEN
      expect(userSopraFormService.getUserSopra).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(userSopraService.update).toHaveBeenCalledWith(expect.objectContaining(userSopra));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserSopra>>();
      const userSopra = { id: 123 };
      jest.spyOn(userSopraFormService, 'getUserSopra').mockReturnValue({ id: null });
      jest.spyOn(userSopraService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userSopra: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userSopra }));
      saveSubject.complete();

      // THEN
      expect(userSopraFormService.getUserSopra).toHaveBeenCalled();
      expect(userSopraService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserSopra>>();
      const userSopra = { id: 123 };
      jest.spyOn(userSopraService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userSopra });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userSopraService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareResponsable', () => {
      it('Should forward to responsableService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(responsableService, 'compareResponsable');
        comp.compareResponsable(entity, entity2);
        expect(responsableService.compareResponsable).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
