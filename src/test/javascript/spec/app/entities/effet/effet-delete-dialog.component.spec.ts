/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DbgestionPhaTestModule } from '../../../test.module';
import { EffetDeleteDialogComponent } from 'app/entities/effet/effet-delete-dialog.component';
import { EffetService } from 'app/entities/effet/effet.service';

describe('Component Tests', () => {
    describe('Effet Management Delete Component', () => {
        let comp: EffetDeleteDialogComponent;
        let fixture: ComponentFixture<EffetDeleteDialogComponent>;
        let service: EffetService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DbgestionPhaTestModule],
                declarations: [EffetDeleteDialogComponent]
            })
                .overrideTemplate(EffetDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EffetDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EffetService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
