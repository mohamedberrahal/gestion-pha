import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DbgestionPhaSharedModule } from 'app/shared';
import {
    MedicamentComponent,
    MedicamentDetailComponent,
    MedicamentUpdateComponent,
    MedicamentDeletePopupComponent,
    MedicamentDeleteDialogComponent,
    medicamentRoute,
    medicamentPopupRoute
} from './';

const ENTITY_STATES = [...medicamentRoute, ...medicamentPopupRoute];

@NgModule({
    imports: [DbgestionPhaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MedicamentComponent,
        MedicamentDetailComponent,
        MedicamentUpdateComponent,
        MedicamentDeleteDialogComponent,
        MedicamentDeletePopupComponent
    ],
    entryComponents: [MedicamentComponent, MedicamentUpdateComponent, MedicamentDeleteDialogComponent, MedicamentDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DbgestionPhaMedicamentModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
