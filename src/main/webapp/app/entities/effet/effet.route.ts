import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Effet } from 'app/shared/model/effet.model';
import { EffetService } from './effet.service';
import { EffetComponent } from './effet.component';
import { EffetDetailComponent } from './effet-detail.component';
import { EffetUpdateComponent } from './effet-update.component';
import { EffetDeletePopupComponent } from './effet-delete-dialog.component';
import { IEffet } from 'app/shared/model/effet.model';

@Injectable({ providedIn: 'root' })
export class EffetResolve implements Resolve<IEffet> {
    constructor(private service: EffetService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEffet> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Effet>) => response.ok),
                map((effet: HttpResponse<Effet>) => effet.body)
            );
        }
        return of(new Effet());
    }
}

export const effetRoute: Routes = [
    {
        path: '',
        component: EffetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.effet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EffetDetailComponent,
        resolve: {
            effet: EffetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.effet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EffetUpdateComponent,
        resolve: {
            effet: EffetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.effet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EffetUpdateComponent,
        resolve: {
            effet: EffetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.effet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const effetPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EffetDeletePopupComponent,
        resolve: {
            effet: EffetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.effet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
