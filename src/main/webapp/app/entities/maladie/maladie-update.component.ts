import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMaladie } from 'app/shared/model/maladie.model';
import { MaladieService } from './maladie.service';

@Component({
    selector: 'jhi-maladie-update',
    templateUrl: './maladie-update.component.html'
})
export class MaladieUpdateComponent implements OnInit {
    maladie: IMaladie;
    isSaving: boolean;

    constructor(protected maladieService: MaladieService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ maladie }) => {
            this.maladie = maladie;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.maladie.id !== undefined) {
            this.subscribeToSaveResponse(this.maladieService.update(this.maladie));
        } else {
            this.subscribeToSaveResponse(this.maladieService.create(this.maladie));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMaladie>>) {
        result.subscribe((res: HttpResponse<IMaladie>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
