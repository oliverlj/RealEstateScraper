# What does it do ?
This Java program scrapes all French cities' (there are about 35 000) real estate and local infos from the website _MeilleursAgents.com_
It saves them in Java Object format and then formats them in a usable way and outputs them in .CSV format.

## How-to
### Import in Excel
- Mark commas as separators.
- Replace ALL "." by "," in order to make all numbers treated as numbers, and not as text (sorting is very different)

### Import in Sheets
- Mark commas as separators.
- Convert to numbers, dates etc —> yes. Department numbers starting with a 0 will lose it. ex : Ain's dpt number is 01 —> it will become 1. But 75 (Paris) will remain 75.

## Additionnal infos
### What might change in the website ?
- In section __Local Infos__ on any city page in the website, check the dates interval. If it changes, you must update variable `ExtractLocalInfos.datesInterval` accordingly.
