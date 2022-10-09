import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDemandeConge } from '../demande-conge.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../demande-conge.test-samples';

import { DemandeCongeService, RestDemandeConge } from './demande-conge.service';

const requireRestSample: RestDemandeConge = {
  ...sampleWithRequiredData,
  creationDate: sampleWithRequiredData.creationDate?.toJSON(),
  dateDebutConge: sampleWithRequiredData.dateDebutConge?.toJSON(),
  dateFinConge: sampleWithRequiredData.dateFinConge?.toJSON(),
};

describe('DemandeConge Service', () => {
  let service: DemandeCongeService;
  let httpMock: HttpTestingController;
  let expectedResult: IDemandeConge | IDemandeConge[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DemandeCongeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a DemandeConge', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const demandeConge = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(demandeConge).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DemandeConge', () => {
      const demandeConge = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(demandeConge).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DemandeConge', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DemandeConge', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DemandeConge', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDemandeCongeToCollectionIfMissing', () => {
      it('should add a DemandeConge to an empty array', () => {
        const demandeConge: IDemandeConge = sampleWithRequiredData;
        expectedResult = service.addDemandeCongeToCollectionIfMissing([], demandeConge);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeConge);
      });

      it('should not add a DemandeConge to an array that contains it', () => {
        const demandeConge: IDemandeConge = sampleWithRequiredData;
        const demandeCongeCollection: IDemandeConge[] = [
          {
            ...demandeConge,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDemandeCongeToCollectionIfMissing(demandeCongeCollection, demandeConge);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DemandeConge to an array that doesn't contain it", () => {
        const demandeConge: IDemandeConge = sampleWithRequiredData;
        const demandeCongeCollection: IDemandeConge[] = [sampleWithPartialData];
        expectedResult = service.addDemandeCongeToCollectionIfMissing(demandeCongeCollection, demandeConge);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeConge);
      });

      it('should add only unique DemandeConge to an array', () => {
        const demandeCongeArray: IDemandeConge[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const demandeCongeCollection: IDemandeConge[] = [sampleWithRequiredData];
        expectedResult = service.addDemandeCongeToCollectionIfMissing(demandeCongeCollection, ...demandeCongeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const demandeConge: IDemandeConge = sampleWithRequiredData;
        const demandeConge2: IDemandeConge = sampleWithPartialData;
        expectedResult = service.addDemandeCongeToCollectionIfMissing([], demandeConge, demandeConge2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeConge);
        expect(expectedResult).toContain(demandeConge2);
      });

      it('should accept null and undefined values', () => {
        const demandeConge: IDemandeConge = sampleWithRequiredData;
        expectedResult = service.addDemandeCongeToCollectionIfMissing([], null, demandeConge, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeConge);
      });

      it('should return initial array if no DemandeConge is added', () => {
        const demandeCongeCollection: IDemandeConge[] = [sampleWithRequiredData];
        expectedResult = service.addDemandeCongeToCollectionIfMissing(demandeCongeCollection, undefined, null);
        expect(expectedResult).toEqual(demandeCongeCollection);
      });
    });

    describe('compareDemandeConge', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDemandeConge(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDemandeConge(entity1, entity2);
        const compareResult2 = service.compareDemandeConge(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDemandeConge(entity1, entity2);
        const compareResult2 = service.compareDemandeConge(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDemandeConge(entity1, entity2);
        const compareResult2 = service.compareDemandeConge(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
