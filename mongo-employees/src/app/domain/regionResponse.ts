import { Region } from "../entities/regions";

export class RegionResponse {

    success: boolean;
    message: String;
    regions: Array<Region>;

}