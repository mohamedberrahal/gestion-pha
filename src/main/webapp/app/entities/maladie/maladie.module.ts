import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DbgestionPhaSharedModule } from 'app/shared';
import {
    MaladieComponent,
    MaladieDetailComponent,
    MaladieUpdateComponent,
    MaladieDeletePopupComponent,
    MaladieDeleteDialogComponent,
    maladieRoute,
    maladiePopupRoute
} from './';

const ENTITY_STATES = [...maladieRoute, ...maladiePopupRoute];

@NgModule({
    imports: [DbgestionPhaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MaladieComponent,
        MaladieDetailComponent,
        MaladieUpdateComponent,
        MaladieDeleteDialogComponent,
        MaladieDeletePopupComponent
    ],
    entryComponents: [MaladieComponent, MaladieUpdateComponent, MaladieDeleteDialogComponent, MaladieDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DbgestionPhaMaladieModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
