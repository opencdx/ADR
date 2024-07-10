/*
 * Copyright 2024 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.adr.model;

public enum DurationType {
    DURATION_TYPE_NOT_SPECIFIED,
    DURATION_TYPE_MILLISECONDS,
    DURATION_TYPE_SECONDS,
    DURATION_TYPE_MINUTES,
    DURATION_TYPE_HOURS,
    DURATION_TYPE_DAYS,
    DURATION_TYPE_WEEKS,
    DURATION_TYPE_MONTHS,
    DURATION_TYPE_YEARS
}
