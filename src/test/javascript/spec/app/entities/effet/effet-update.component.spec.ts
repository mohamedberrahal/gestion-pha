/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DbgestionPhaTestModule } from '../../../test.module';
import { EffetUpdateComponent } from 'app/entities/effet/effet-update.component';
import { EffetService } from 'app/entities/effet/effet.service';
import { Effet } from 'app/shared/model/effet.model';

describe('Component Tests', () => {
    describe('Effet Management Update Component', () => {
        let comp: EffetUpdateComponent;
        let fixture: ComponentFixture<EffetUpdateComponent>;
        let service: EffetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DbgestionPhaTestModule],
                declarations: [EffetUpdateComponent]
            })
                .overrideTemplate(EffetUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EffetUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EffetService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Effet(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.effet = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Effet();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.effet = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
