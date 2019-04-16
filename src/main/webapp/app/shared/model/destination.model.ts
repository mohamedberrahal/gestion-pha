import { IMedicament } from 'app/shared/model/medicament.model';

export interface IDestination {
    id?: number;
    population?: string;
    medicament?: IMedicament;
}

export class Destination implements IDestination {
    constructor(public id?: number, public population?: string, public medicament?: IMedicament) {}
}
