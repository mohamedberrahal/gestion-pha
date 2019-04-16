/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DbgestionPhaTestModule } from '../../../test.module';
import { EffetComponent } from 'app/entities/effet/effet.component';
import { EffetService } from 'app/entities/effet/effet.service';
import { Effet } from 'app/shared/model/effet.model';

describe('Component Tests', () => {
    describe('Effet Management Component', () => {
        let comp: EffetComponent;
        let fixture: ComponentFixture<EffetComponent>;
        let service: EffetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DbgestionPhaTestModule],
                declarations: [EffetComponent],
                providers: []
            })
                .overrideTemplate(EffetComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EffetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EffetService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Effet(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.effets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
