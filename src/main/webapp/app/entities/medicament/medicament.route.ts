import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Medicament } from 'app/shared/model/medicament.model';
import { MedicamentService } from './medicament.service';
import { MedicamentComponent } from './medicament.component';
import { MedicamentDetailComponent } from './medicament-detail.component';
import { MedicamentUpdateComponent } from './medicament-update.component';
import { MedicamentDeletePopupComponent } from './medicament-delete-dialog.component';
import { IMedicament } from 'app/shared/model/medicament.model';

@Injectable({ providedIn: 'root' })
export class MedicamentResolve implements Resolve<IMedicament> {
    constructor(private service: MedicamentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMedicament> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Medicament>) => response.ok),
                map((medicament: HttpResponse<Medicament>) => medicament.body)
            );
        }
        return of(new Medicament());
    }
}

export const medicamentRoute: Routes = [
    {
        path: '',
        component: MedicamentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.medicament.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MedicamentDetailComponent,
        resolve: {
            medicament: MedicamentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.medicament.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MedicamentUpdateComponent,
        resolve: {
            medicament: MedicamentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.medicament.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MedicamentUpdateComponent,
        resolve: {
            medicament: MedicamentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.medicament.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const medicamentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MedicamentDeletePopupComponent,
        resolve: {
            medicament: MedicamentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.medicament.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
