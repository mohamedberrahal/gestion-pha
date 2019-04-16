import { IMedicament } from 'app/shared/model/medicament.model';

export interface IEffet {
    id?: number;
    libelle?: string;
    discription?: string;
    medicament?: IMedicament;
}

export class Effet implements IEffet {
    constructor(public id?: number, public libelle?: string, public discription?: string, public medicament?: IMedicament) {}
}
