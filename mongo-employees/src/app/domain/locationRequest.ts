import { Locations } from "../entities/locations";

export class LocationRequest {

    update: boolean;
    location: Locations;

    constructor() {
        this.update = false;
        this.location = new Locations();
    }
}