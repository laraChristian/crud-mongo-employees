import { Country } from "../entities/country";

export class CountryRequest {

    update: boolean;
    country: Country;

    constructor() {
        this.update = false;
        this.country = new Country();
    }
}