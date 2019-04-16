import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicament } from 'app/shared/model/medicament.model';

@Component({
    selector: 'jhi-medicament-detail',
    templateUrl: './medicament-detail.component.html'
})
export class MedicamentDetailComponent implements OnInit {
    medicament: IMedicament;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ medicament }) => {
            this.medicament = medicament;
        });
    }

    previousState() {
        window.history.back();
    }
}
