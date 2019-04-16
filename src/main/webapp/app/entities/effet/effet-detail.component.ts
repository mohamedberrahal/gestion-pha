import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEffet } from 'app/shared/model/effet.model';

@Component({
    selector: 'jhi-effet-detail',
    templateUrl: './effet-detail.component.html'
})
export class EffetDetailComponent implements OnInit {
    effet: IEffet;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ effet }) => {
            this.effet = effet;
        });
    }

    previousState() {
        window.history.back();
    }
}
