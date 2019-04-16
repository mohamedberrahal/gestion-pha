import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEffet } from 'app/shared/model/effet.model';
import { AccountService } from 'app/core';
import { EffetService } from './effet.service';

@Component({
    selector: 'jhi-effet',
    templateUrl: './effet.component.html'
})
export class EffetComponent implements OnInit, OnDestroy {
    effets: IEffet[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected effetService: EffetService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.effetService
            .query()
            .pipe(
                filter((res: HttpResponse<IEffet[]>) => res.ok),
                map((res: HttpResponse<IEffet[]>) => res.body)
            )
            .subscribe(
                (res: IEffet[]) => {
                    this.effets = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEffets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEffet) {
        return item.id;
    }

    registerChangeInEffets() {
        this.eventSubscriber = this.eventManager.subscribe('effetListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
