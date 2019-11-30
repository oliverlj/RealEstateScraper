## What does it do ?
This Java program scrapes all French cities' (there are about 35 000) real estate and local infos from the website _MeilleursAgents.com_  
It saves them in Java Object format and then formats them in a usable way and outputs them in .CSV format.  
It also handles Private Internet Acces VPN to change IP as much as needed to bypass the website's limitations until all pages are scraped.  

## What's new ?
### To-do
- Minimise time of calculation for saturated IPs
    - Try to play with `nbRetries` to get retry percentage 
- For each IP, calculate delay and sort it automatically
- Detect max number of pools using percentages and nb max after which the IP is considered drained.
- Fix problem with program not loading if VPN deactivated —> run with PIA already on for now
- Record exactly all the dates and time where the IP address has been tested and its disponibility for each datetime
### Done
- Handling PIA VPN `piactl` command line utility that has been added in update 1.6, instead of horrible manual AppleScript clicking —> way cleaner and resilient

## How to...?
### Import in Excel
- Mark commas as separators.
- Replace ALL "." by "," in order to make all numbers treated as numbers, and not as text (sorting is very different)

### Import in Google Sheets
- Mark commas as separators.
- Convert to numbers, dates etc —> yes. Department numbers starting with a 0 will lose it. ex : Ain's dpt number is 01 —> it will become 1. But 75 (Paris) will remain 75.

## Additionnal infos
### What might change in the website ?
- In section __Local Infos__ on any city page in the website, check the dates interval. If it changes, you must update variable `ExtractLocalInfos.datesInterval` accordingly.