import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUserSopra } from '../user-sopra.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../user-sopra.test-samples';

import { UserSopraService } from './user-sopra.service';

const requireRestSample: IUserSopra = {
  ...sampleWithRequiredData,
};

describe('UserSopra Service', () => {
  let service: UserSopraService;
  let httpMock: HttpTestingController;
  let expectedResult: IUserSopra | IUserSopra[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserSopraService);
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

    it('should create a UserSopra', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const userSopra = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(userSopra).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserSopra', () => {
      const userSopra = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(userSopra).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UserSopra', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UserSopra', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a UserSopra', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUserSopraToCollectionIfMissing', () => {
      it('should add a UserSopra to an empty array', () => {
        const userSopra: IUserSopra = sampleWithRequiredData;
        expectedResult = service.addUserSopraToCollectionIfMissing([], userSopra);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userSopra);
      });

      it('should not add a UserSopra to an array that contains it', () => {
        const userSopra: IUserSopra = sampleWithRequiredData;
        const userSopraCollection: IUserSopra[] = [
          {
            ...userSopra,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUserSopraToCollectionIfMissing(userSopraCollection, userSopra);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserSopra to an array that doesn't contain it", () => {
        const userSopra: IUserSopra = sampleWithRequiredData;
        const userSopraCollection: IUserSopra[] = [sampleWithPartialData];
        expectedResult = service.addUserSopraToCollectionIfMissing(userSopraCollection, userSopra);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userSopra);
      });

      it('should add only unique UserSopra to an array', () => {
        const userSopraArray: IUserSopra[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const userSopraCollection: IUserSopra[] = [sampleWithRequiredData];
        expectedResult = service.addUserSopraToCollectionIfMissing(userSopraCollection, ...userSopraArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userSopra: IUserSopra = sampleWithRequiredData;
        const userSopra2: IUserSopra = sampleWithPartialData;
        expectedResult = service.addUserSopraToCollectionIfMissing([], userSopra, userSopra2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userSopra);
        expect(expectedResult).toContain(userSopra2);
      });

      it('should accept null and undefined values', () => {
        const userSopra: IUserSopra = sampleWithRequiredData;
        expectedResult = service.addUserSopraToCollectionIfMissing([], null, userSopra, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userSopra);
      });

      it('should return initial array if no UserSopra is added', () => {
        const userSopraCollection: IUserSopra[] = [sampleWithRequiredData];
        expectedResult = service.addUserSopraToCollectionIfMissing(userSopraCollection, undefined, null);
        expect(expectedResult).toEqual(userSopraCollection);
      });
    });

    describe('compareUserSopra', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUserSopra(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUserSopra(entity1, entity2);
        const compareResult2 = service.compareUserSopra(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUserSopra(entity1, entity2);
        const compareResult2 = service.compareUserSopra(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUserSopra(entity1, entity2);
        const compareResult2 = service.compareUserSopra(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
