/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MedicamentService } from 'app/entities/medicament/medicament.service';
import { IMedicament, Medicament } from 'app/shared/model/medicament.model';

describe('Service Tests', () => {
    describe('Medicament Service', () => {
        let injector: TestBed;
        let service: MedicamentService;
        let httpMock: HttpTestingController;
        let elemDefault: IMedicament;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(MedicamentService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Medicament(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateExpiraction: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Medicament', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateExpiraction: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateExpiraction: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Medicament(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Medicament', async () => {
                const returnedFromService = Object.assign(
                    {
                        nom: 'BBBBBB',
                        libelle: 'BBBBBB',
                        discription: 'BBBBBB',
                        prix: 1,
                        dateExpiraction: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateExpiraction: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Medicament', async () => {
                const returnedFromService = Object.assign(
                    {
                        nom: 'BBBBBB',
                        libelle: 'BBBBBB',
                        discription: 'BBBBBB',
                        prix: 1,
                        dateExpiraction: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateExpiraction: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Medicament', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
