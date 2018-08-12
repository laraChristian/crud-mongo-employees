import { Region } from "../entities/regions";

export class RegionRequest {

    update: boolean;
    region: Region;

    constructor() {
        this.update = false;
        this.region = new Region();
    }
}