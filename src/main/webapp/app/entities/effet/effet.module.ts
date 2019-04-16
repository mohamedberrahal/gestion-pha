import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DbgestionPhaSharedModule } from 'app/shared';
import {
    EffetComponent,
    EffetDetailComponent,
    EffetUpdateComponent,
    EffetDeletePopupComponent,
    EffetDeleteDialogComponent,
    effetRoute,
    effetPopupRoute
} from './';

const ENTITY_STATES = [...effetRoute, ...effetPopupRoute];

@NgModule({
    imports: [DbgestionPhaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [EffetComponent, EffetDetailComponent, EffetUpdateComponent, EffetDeleteDialogComponent, EffetDeletePopupComponent],
    entryComponents: [EffetComponent, EffetUpdateComponent, EffetDeleteDialogComponent, EffetDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DbgestionPhaEffetModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
