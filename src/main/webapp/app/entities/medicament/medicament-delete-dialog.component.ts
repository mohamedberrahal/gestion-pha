import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicament } from 'app/shared/model/medicament.model';
import { MedicamentService } from './medicament.service';

@Component({
    selector: 'jhi-medicament-delete-dialog',
    templateUrl: './medicament-delete-dialog.component.html'
})
export class MedicamentDeleteDialogComponent {
    medicament: IMedicament;

    constructor(
        protected medicamentService: MedicamentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.medicamentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'medicamentListModification',
                content: 'Deleted an medicament'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-medicament-delete-popup',
    template: ''
})
export class MedicamentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ medicament }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MedicamentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.medicament = medicament;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/medicament', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/medicament', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
