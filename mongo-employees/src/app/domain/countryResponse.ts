import { Country } from "../entities/country";

export class CountryResponse {

    success: boolean;
    message: String;
    countries: Array<Country>;

}