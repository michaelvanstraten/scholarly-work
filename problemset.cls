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

% Define mathematical symbols and operations
\newcommand{\Id}[1]{\operatorname{Id}_{#1}}
\newcommand{\field}[1]{\mathbb{#1}}
\newcommand{\nats}{\field{N}}
\newcommand{\ints}{\field{Z}}
\newcommand{\reals}{\field{R}}
\newcommand{\evens}{\field{G}}
\newcommand{\after}{\circ}
\newcommand{\with}{\mid}
\newcommand{\union}{\cup}
\newcommand{\Union}{\bigcup}
\newcommand{\intersection}{\cap}
\newcommand{\Intersection}{\bigcap}
\newcommand{\powerset}[1]{\mathcal{P}(#1)}
\newcommand{\symdiff}{\vartriangle}

% Define paired delimiters and intervals
\DeclarePairedDelimiterXPP{\paren}[1]{}{\lparen}{\rparen}{}{#1}
\DeclarePairedDelimiterXPP{\seq}[2]{}{\lparen}{\rparen}{_{#2}}{#1}
\DeclarePairedDelimiterXPP{\set}[1]{}{\lbrace}{\rbrace}{}{#1}
\DeclarePairedDelimiterXPP{\abs}[1]{}{\lvert}{\rvert}{}{#1}
\DeclarePairedDelimiterXPP{\card}[1]{}{\lvert}{\rvert}{}{#1}
\DeclarePairedDelimiterXPP{\inner}[2]{}{\langle}{\rangle}{}{#1, #2}
\DeclarePairedDelimiterXPP{\openinterval}[2]{}{\rbrack}{\lbrack}{}{#1, #2}
\DeclarePairedDelimiterXPP{\leftopeninterval}[2]{}{\rbrack}{\rbrack}{}{#1, #2}
\DeclarePairedDelimiterXPP{\rightopeninterval}[2]{}{\lbrack}{\lbrack}{}{#1, #2}
\DeclarePairedDelimiterXPP{\closedinterval}[2]{}{\lbrack}{\rbrack}{}{#1, #2}

% Set document spacing
\setlength{\parindent}{0pt}
\setlength{\parskip}{.5em}

% Create custom 'problem' environment and counter 
\newcounter{problem}
\newenvironment{problem}[2][]{
	\stepcounter{problem}
	\par\noindent\textbf{Aufgabe~\theproblem:}~#1\hfill[\textbf{#2}]\par
	\fancyhead[R]{
		\@author \\
		\textit{Aufgabe~\theproblem}
	}
}{
	\pagebreak
	\fancyhead[R]{
		\@author \\
	}
}

% Define a custom theorem style for definitions
\theoremstyle{definition}
\newtheorem{definition}{Definition}

