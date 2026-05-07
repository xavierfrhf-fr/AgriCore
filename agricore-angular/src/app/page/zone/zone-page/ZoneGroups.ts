import { ZoneDTO } from '../../../dto/zone/response/zone-dto';
import { ZoneDataDTO } from '../../../dto/zone/response/zone-data-dto';

export type ZoneWithData = {
  zone: ZoneDTO;
  zoneData?: ZoneDataDTO;
};

export type ZoneGroups = {
  plantZone: ZoneWithData[];
  animalZone: ZoneWithData[];
  utilityZone: ZoneWithData[];
  storageZone: ZoneWithData[];
};
