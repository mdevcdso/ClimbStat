import { ClimbingGymDto } from "./climbingGym.dto";
import { PartialType } from "@nestjs/mapped-types";

export class UpdateClimbingGymDto extends PartialType(ClimbingGymDto){}