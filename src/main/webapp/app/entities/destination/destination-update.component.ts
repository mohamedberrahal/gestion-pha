import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDestination } from 'app/shared/model/destination.model';
import { DestinationService } from './destination.service';
import { IMedicament } from 'app/shared/model/medicament.model';
import { MedicamentService } from 'app/entities/medicament';

@Component({
    selector: 'jhi-destination-update',
    templateUrl: './destination-update.component.html'
})
export class DestinationUpdateComponent implements OnInit {
    destination: IDestination;
    isSaving: boolean;

    medicaments: IMedicament[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected destinationService: DestinationService,
        protected medicamentService: MedicamentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ destination }) => {
            this.destination = destination;
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
        if (this.destination.id !== undefined) {
            this.subscribeToSaveResponse(this.destinationService.update(this.destination));
        } else {
            this.subscribeToSaveResponse(this.destinationService.create(this.destination));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDestination>>) {
        result.subscribe((res: HttpResponse<IDestination>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
