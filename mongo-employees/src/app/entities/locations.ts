import { Country } from "./country";

export class Locations {

    id: String;
    streetAddress: String;
    postalCode: String;
    city: String;
    stateProvince: String;
    countryId: String;
    countryName: String;

    constructor() {
        this.countryId = '';
    }
}