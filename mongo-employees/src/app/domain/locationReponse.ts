import { Locations } from "../entities/locations";

export class LocationResponse {

    success: boolean;
    message: String;
    locations: Array<Locations>;

}