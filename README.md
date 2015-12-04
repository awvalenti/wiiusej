# wiiusej
A Java API for Wiimotes, fork of [guiguito's wiiusej](https://github.com/guiguito/wiiusej), originally on [Google Code](https://code.google.com/p/wiiusej/)

## External library
wiiusej includes binaries from
[wiiuse](http://www.macs.hw.ac.uk/~ruth/year4VEs/Labs/wiiuse.html).
wiiuse is a native Wiimote library written in C.

## Changes from original version
- Project splitted into Maven modules
- Detecting operating system automatically
- Unpacking appropriate native libraries from JAR file to temp directory

## License
[GPL v3](http://www.gnu.org/licenses/gpl-3.0.en.html)

```
wiiusej - Java API for Wiimotes
Copyright (C) 2015  Andre Willik Valenti
Copyright (C) 2008  Guilhem Duché

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
```
