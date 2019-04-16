/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DbgestionPhaTestModule } from '../../../test.module';
import { EffetDetailComponent } from 'app/entities/effet/effet-detail.component';
import { Effet } from 'app/shared/model/effet.model';

describe('Component Tests', () => {
    describe('Effet Management Detail Component', () => {
        let comp: EffetDetailComponent;
        let fixture: ComponentFixture<EffetDetailComponent>;
        const route = ({ data: of({ effet: new Effet(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DbgestionPhaTestModule],
                declarations: [EffetDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EffetDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EffetDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.effet).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
