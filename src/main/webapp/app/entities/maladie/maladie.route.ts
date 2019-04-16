import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Maladie } from 'app/shared/model/maladie.model';
import { MaladieService } from './maladie.service';
import { MaladieComponent } from './maladie.component';
import { MaladieDetailComponent } from './maladie-detail.component';
import { MaladieUpdateComponent } from './maladie-update.component';
import { MaladieDeletePopupComponent } from './maladie-delete-dialog.component';
import { IMaladie } from 'app/shared/model/maladie.model';

@Injectable({ providedIn: 'root' })
export class MaladieResolve implements Resolve<IMaladie> {
    constructor(private service: MaladieService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMaladie> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Maladie>) => response.ok),
                map((maladie: HttpResponse<Maladie>) => maladie.body)
            );
        }
        return of(new Maladie());
    }
}

export const maladieRoute: Routes = [
    {
        path: '',
        component: MaladieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.maladie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MaladieDetailComponent,
        resolve: {
            maladie: MaladieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.maladie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MaladieUpdateComponent,
        resolve: {
            maladie: MaladieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.maladie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MaladieUpdateComponent,
        resolve: {
            maladie: MaladieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.maladie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const maladiePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MaladieDeletePopupComponent,
        resolve: {
            maladie: MaladieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'dbgestionPhaApp.maladie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
