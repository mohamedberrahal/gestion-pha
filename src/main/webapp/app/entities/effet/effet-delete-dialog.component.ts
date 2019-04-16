import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEffet } from 'app/shared/model/effet.model';
import { EffetService } from './effet.service';

@Component({
    selector: 'jhi-effet-delete-dialog',
    templateUrl: './effet-delete-dialog.component.html'
})
export class EffetDeleteDialogComponent {
    effet: IEffet;

    constructor(protected effetService: EffetService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.effetService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'effetListModification',
                content: 'Deleted an effet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-effet-delete-popup',
    template: ''
})
export class EffetDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ effet }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EffetDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.effet = effet;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/effet', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/effet', { outlets: { popup: null } }]);
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
