import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEffet } from 'app/shared/model/effet.model';
import { EffetService } from './effet.service';
import { IMedicament } from 'app/shared/model/medicament.model';
import { MedicamentService } from 'app/entities/medicament';

@Component({
    selector: 'jhi-effet-update',
    templateUrl: './effet-update.component.html'
})
export class EffetUpdateComponent implements OnInit {
    effet: IEffet;
    isSaving: boolean;

    medicaments: IMedicament[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected effetService: EffetService,
        protected medicamentService: MedicamentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ effet }) => {
            this.effet = effet;
        });
        this.medicamentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMedicament[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMedicament[]>) => response.body)
            )
            .subscribe((res: IMedicament[]) => (this.medicaments = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.effet.id !== undefined) {
            this.subscribeToSaveResponse(this.effetService.update(this.effet));
        } else {
            this.subscribeToSaveResponse(this.effetService.create(this.effet));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEffet>>) {
        result.subscribe((res: HttpResponse<IEffet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackMedicamentById(index: number, item: IMedicament) {
        return item.id;
    }
}
