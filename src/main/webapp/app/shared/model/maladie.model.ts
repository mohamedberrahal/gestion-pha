import { IMedicament } from 'app/shared/model/medicament.model';

export interface IMaladie {
    id?: number;
    maladieName?: string;
    medicaments?: IMedicament[];
}

export class Maladie implements IMaladie {
    constructor(public id?: number, public maladieName?: string, public medicaments?: IMedicament[]) {}
}
