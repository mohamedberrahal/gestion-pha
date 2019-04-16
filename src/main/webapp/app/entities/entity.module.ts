import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'effet',
                loadChildren: './effet/effet.module#DbgestionPhaEffetModule'
            },
            {
                path: 'destination',
                loadChildren: './destination/destination.module#DbgestionPhaDestinationModule'
            },
            {
                path: 'maladie',
                loadChildren: './maladie/maladie.module#DbgestionPhaMaladieModule'
            },
            {
                path: 'medicament',
                loadChildren: './medicament/medicament.module#DbgestionPhaMedicamentModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DbgestionPhaEntityModule {}
