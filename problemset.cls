\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{problemset}[Custom class for problemsets]

\LoadClass[a4paper]{scrartcl} % Inherit from scrartcl class

\RequirePackage[utf8]{inputenc}         % this is needed for umlauts
\RequirePackage[ngerman]{babel}         % this is needed for German translations
\RequirePackage[T1]{fontenc}            % needed for right umlaut output in pdf
\RequirePackage[ngerman, num]{isodate}  % get DD.MM.YYYY dates
\RequirePackage[hidelinks]{hyperref}
\RequirePackage{amsmath, amsthm, amssymb}
\RequirePackage{mathtools}
\RequirePackage{enumitem}
\RequirePackage{fancyhdr}
\RequirePackage[top=2.5cm, bottom=2.5cm, outer=2.0cm, inner=2.0cm, heightrounded, marginparwidth=1cm, marginparsep=0.5cm]{geometry}


\setlist[enumerate, 1]{label=\roman*)}
\setlist[description]{font=\normalfont}

\def\Student#1{\gdef\@Student{#1}}
\def\Lecture#1{\gdef\@Lecture{#1}}
\def\Problemset#1{\gdef\@Problemset{#1}}
\def\DoOn#1{\gdef\@DoOn{#1}}

\def\@Student{Max Musterman}
\def\@Lecture{Lineare Algebra und Analytische Geometrie I}
\def\@Problemset{1}
\def\@DoOn{20. April 1970 16:20 Uhr}

\renewcommand{\maketitle}{
	\thispagestyle{empty}
	\begin{center}
		\Large
		\@Lecture
		\medbreak
		\normalsize
		\textbf{Lösungen}: \@Problemset . Übungsblatt \\
		Abgabe: \@DoOn \\
		\@Student
	\end{center}
	\rule{\textwidth}{.5pt}
}

\pagestyle{fancy}
\lhead{
    \@Lecture \\
    \textbf{Lösungen}: \@Problemset . Übungsblatt
}
\rhead{\@Student}
\cfoot{\thepage}

\newtheoremstyle{problem}% name of the style to be used
{}% measure of space to leave above the theorem. E.g.: 3pt
{}% measure of space to leave below the theorem. E.g.: 3pt
{}% name of font to use in the body of the theorem
{}% measure of space to indent
{\bfseries}% name of head font
{}% punctuation between head and body
{\newline}% space after theorem head; " " = normal interword space
{\thmname{#1}\thmnumber{ #2}: \textnormal{\thmnote{ #3}}}% Manually specify head

\theoremstyle{problem}
\newtheorem{problem}{Aufgabe}

\newtheorem{definition}{Definition}
