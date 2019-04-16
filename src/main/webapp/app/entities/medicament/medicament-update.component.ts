import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IMedicament } from 'app/shared/model/medicament.model';
import { MedicamentService } from './medicament.service';
import { IMaladie } from 'app/shared/model/maladie.model';
import { MaladieService } from 'app/entities/maladie';

@Component({
    selector: 'jhi-medicament-update',
    templateUrl: './medicament-update.component.html'
})
export class MedicamentUpdateComponent implements OnInit {
    medicament: IMedicament;
    isSaving: boolean;

    maladies: IMaladie[];
    dateExpiraction: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected medicamentService: MedicamentService,
        protected maladieService: MaladieService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ medicament }) => {
            this.medicament = medicament;
            this.dateExpiraction =
                this.medicament.dateExpiraction != null ? this.medicament.dateExpiraction.format(DATE_TIME_FORMAT) : null;
        });
        this.maladieService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMaladie[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMaladie[]>) => response.body)
            )
            .subscribe((res: IMaladie[]) => (this.maladies = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.medicament.dateExpiraction = this.dateExpiraction != null ? moment(this.dateExpiraction, DATE_TIME_FORMAT) : null;
        if (this.medicament.id !== undefined) {
            this.subscribeToSaveResponse(this.medicamentService.update(this.medicament));
        } else {
            this.subscribeToSaveResponse(this.medicamentService.create(this.medicament));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicament>>) {
        result.subscribe((res: HttpResponse<IMedicament>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMaladieById(index: number, item: IMaladie) {
        return item.id;
    }
}
