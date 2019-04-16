import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { DbgestionPhaSharedModule } from 'app/shared';
import {
    DestinationComponent,
    DestinationDetailComponent,
    DestinationUpdateComponent,
    DestinationDeletePopupComponent,
    DestinationDeleteDialogComponent,
    destinationRoute,
    destinationPopupRoute
} from './';

const ENTITY_STATES = [...destinationRoute, ...destinationPopupRoute];

@NgModule({
    imports: [DbgestionPhaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DestinationComponent,
        DestinationDetailComponent,
        DestinationUpdateComponent,
        DestinationDeleteDialogComponent,
        DestinationDeletePopupComponent
    ],
    entryComponents: [DestinationComponent, DestinationUpdateComponent, DestinationDeleteDialogComponent, DestinationDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DbgestionPhaDestinationModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
