% Custom LaTeX class for problem sets
\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{problemset}[Custom class for problem sets]

% Load the KOMA-Script article class with A4 paper size
\LoadClass[a4paper]{scrartcl}

% Required packages
\RequirePackage[ngerman]{babel}
\RequirePackage[ngerman, num]{isodate}
\RequirePackage{amsmath}
\RequirePackage{amssymb}
\RequirePackage{amsthm, thmtools}
\RequirePackage{mathtools}
\RequirePackage{extramarks}
\RequirePackage{enumitem}
\RequirePackage{fancyhdr}
\RequirePackage{xpatch}
\RequirePackage[top=2.5cm, bottom=2.5cm, outer=2.0cm, inner=2.0cm, heightrounded, marginparwidth=1cm, marginparsep=0.5cm]{geometry}
\RequirePackage[hidelinks, bookmarksopen=true]{hyperref}
\RequirePackage[open]{bookmark}

% Set the label format for the first level of enumerate
\setlist[enumerate, 1]{label=\roman*)}
\setlist[description]{font=\normalfont}

% Reset the equation counter on every call to align to start from 1 every time
\AtBeginEnvironment{align}{\setcounter{equation}{0}}

% Define commands for lecture, problem set, and submission date
\newcommand{\Lecture}[1]{\gdef\@Lecture{#1}}
\newcommand{\Problemset}[1]{\gdef\@Problemset{#1}}
\newcommand{\DoOn}[1]{\gdef\@DoOn{#1}}

% Default values for lecture, problem set, and submission date
\def\@Lecture{Foo Bar}
\def\@Problemset{1}
\def\@DoOn{1. Januar 1970 00:00 Uhr}

% Set up document title and metadata
\AtBeginDocument{
	\title{\@Problemset. Aufgabenblatt - \@Lecture}
	\hypersetup{
		pdftitle={\@title},
		pdfauthor={\@author},
	}
}

% Redefine the maketitle command
\renewcommand{\maketitle}{
	\thispagestyle{empty}
	\begin{center}
		\Large
		\@Lecture
		\medbreak
		\normalsize
		\textbf{Lösungen}: \@Problemset . Übungsblatt \\
		Abgabe: \@DoOn \\
		\@author
	\end{center}
	\rule{\textwidth}{.5pt}
}

% Configure the page style using fancyhdr
\pagestyle{fancy}
\fancyhead[L]{
	\@Lecture \\
	\textbf{Lösungen}:\space\@Problemset. Übungsblatt
}
\fancyhead[R]{
	\@author \\
}
\fancyfoot[C]{\thepage}

% Create custom 'problem' environment and counter 
\newcounter{problem}
\newenvironment{problem}[2][]{
	\stepcounter{problem}
	\par\noindent\textbf{Aufgabe~\theproblem:}~#1\hfill[\textbf{#2}]\par
}{
	\pagebreak
}


\xapptocmd{\proof}{\mbox{}\par\nobreak}{}{}
